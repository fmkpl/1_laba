package by.patsei.sprringbooks.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    public String title;
    public String country;

    public Movie(String title) {
        this.title = title;
    }
}
