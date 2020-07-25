package pl.endproject.offerscomparator.subdomains.userRegistration.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long log_id;

    private Date date;
    private String description;
    private Long user_id;
    private String source;
    private String type;

/*
    todo: relacje pommiedzy logerem a userem
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

*/




}
