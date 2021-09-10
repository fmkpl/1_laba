package by.patsei.sprringbooks.forms;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieForm {
    public String title;
    public String country;

    public String newTitle;
    public String newCountry;
}
