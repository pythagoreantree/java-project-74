package hexlet.code.app.models;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long id;
    @CreatedDate
    public Instant createdAt;
}
