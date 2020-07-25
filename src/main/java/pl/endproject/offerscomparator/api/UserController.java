package pl.endproject.offerscomparator.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.endproject.offerscomparator.subdomains.userProfile.dao.ProfileDao;
import pl.endproject.offerscomparator.subdomains.userProfile.model.Address;
import pl.endproject.offerscomparator.subdomains.userProfile.model.BasicInformation;
import pl.endproject.offerscomparator.subdomains.userProfile.model.Profile;
import pl.endproject.offerscomparator.subdomains.userRegistration.model.User;
import pl.endproject.offerscomparator.subdomains.userRegistration.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final ProfileDao profileDao;


    @Autowired
    public UserController(UserServiceImpl userService, ProfileDao profileDao) {
        this.userService = userService;
        this.profileDao = profileDao;
    }


    @GetMapping("/signup")
    public String showSignUpForm(HttpServletResponse response, Model model, @RequestParam(value = "token", required = false, defaultValue = "") String token) throws IOException {
        if (StringUtils.isNotEmpty(token)) {
            if (userService.activateUser(token)) {
                response.getWriter().println("Thank you for activating your account!");
                response.setStatus(200);
                return "white-page";
            } else {
                response.getWriter().println("Something went wrong :(");
                response.setStatus(400);
                return "white-page";
            }
        }


        return "add-user";
    }

    @PostMapping("/signup")
    public String addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String path = String.valueOf(request.getRequestURL());

        if (!userService.registerUser(login, email, password, path)) {
            model.addAttribute("failure", userService.getFailureCause());
            response.setStatus(409);
            return "add-user";
        }

        response.getWriter().println("Look for the verification email in your inbox and click the link in that email. <br>A confirmation message will appear in your web browser.");
        response.setStatus(200);
        return "white-page";
    }


    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password) {

        User loginUser = userService.loginUser(username, password);
        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser);
        } else {
            System.out.println("User doesn't exists");
        }
        return "redirect:/offers";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("loginUser", null);
        return "redirect:/offers";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/offers";
        } else {
            if (!profileDao.existsByEmail(loginUser.getEmail())) {
                profileDao.save(new Profile(loginUser.getEmail(), loginUser.getLogin(), loginUser.getRole(), new BasicInformation(), new Address()));
            }
            Profile userProfile = profileDao.findProfileByEmail(loginUser.getEmail());
            session.setAttribute("userProfile", userProfile);
            return "user-profile";
        }
    }

    @PostMapping("/profile")
    public String profile(HttpServletRequest request, HttpSession session) throws IOException {

        String action = request.getParameter("action");
        Profile userProfile = (Profile) session.getAttribute("userProfile");
        switch (action) {
            case "basicInformation":
                userProfile.getBasicInformation().setFirstName(request.getParameter("firstName"));
                userProfile.getBasicInformation().setLastName(request.getParameter("lastName"));
                userProfile.getBasicInformation().setGender(request.getParameter("gender"));
                userProfile.getBasicInformation().setPhoneNumber(request.getParameter("phoneNumber"));
                if (!request.getParameter("birthDate").isEmpty()) {
                    userProfile.getBasicInformation().setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
                }

                break;
            case "address":
                userProfile.getAddress().setAddress1(request.getParameter("address1"));
                userProfile.getAddress().setAddress2(request.getParameter("address2"));
                userProfile.getAddress().setZipCode(request.getParameter("zipCode"));
                userProfile.getAddress().setCityTown(request.getParameter("cityTown"));
                userProfile.getAddress().setCountry(request.getParameter("country"));

                break;
        }

        profileDao.save(userProfile);
        return "redirect:/profile";
    }
}
