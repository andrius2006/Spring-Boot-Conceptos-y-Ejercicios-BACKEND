package models; 

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    private String id;

    private String nombre;

    @Indexed(unique = true)
    private String email;

    private String password;

    @Builder.Default
    private Rol rol = Rol.USER;

    @Builder.Default
    private LocalDateTime creadoEn = LocalDateTime.now();

    public enum Rol {
        USER, ADMIN
    }
}


