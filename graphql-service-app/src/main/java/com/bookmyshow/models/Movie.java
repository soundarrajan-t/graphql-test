package com.bookmyshow.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@SuppressWarnings({"unused"})
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private String cast;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Certification certification;

    @NonNull
    private String genre;

    @NonNull
    private Integer duration;

}



