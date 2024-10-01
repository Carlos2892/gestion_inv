package com.vargas.gestioninventario.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="marcas")
@Data //para los get y set - LOMBOK
@AllArgsConstructor // construcctor con todos los campos - LOMBOK
@NoArgsConstructor // constructor vacio (sin campos) - LOMBOK
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    private String estado;

    @OneToMany(mappedBy = "marca")
    private Set<Producto> productos;

}