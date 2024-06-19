package david_seu.your_anime_list_backend.model.utils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public enum Type {
    TV, MOVIE, OVA, ONA, SPECIAL, UNKNOWN
}