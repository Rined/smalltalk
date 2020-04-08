package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.dto.Views;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usr")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @JsonView(Views.IdName.class)
    private String id;

    @Column(name = "name")
    @JsonView(Views.IdName.class)
    private String name;

    @Column(name = "userpic")
    @JsonView(Views.IdName.class)
    private String picture;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locale")
    private String locale;

    @Column(name = "last_visit")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

}
