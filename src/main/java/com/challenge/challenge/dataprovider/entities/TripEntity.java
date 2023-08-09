package com.challenge.challenge.dataprovider.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_information")
    private Long idInformations;

    private LocalDateTime tpep_pickup_datetime;
    private LocalDateTime tpep_dropoff_datetime;
    private Long PULocationID;
    private Long DOLocationID;

}
