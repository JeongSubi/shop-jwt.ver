package com.subi.shop.notification;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String title;

    @Column(nullable = false)
    public Date date;
}
