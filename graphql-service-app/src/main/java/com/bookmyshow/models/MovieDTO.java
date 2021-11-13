package com.bookmyshow.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class MovieDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 250)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 250)
    private String description;

    @NotBlank(message = "Cast is required")
    private String cast;

    @NotNull
    private Certification certification;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull
    @Range(min = 60, max = 240)
    private Integer duration;

    @Range(min = 0, max = 10)
    private Double rating;

}
