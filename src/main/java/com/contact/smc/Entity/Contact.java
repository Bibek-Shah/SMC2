package com.contact.smc.Entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    private String contactId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImagePublicId;


    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contact",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)

    private List<SocialLink> groups = new ArrayList<>();
}
