package com.example.sae_mobile_api.sae_mobile_api.repositorys;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sae_mobile_api.sae_mobile_api.models.Medicament;
import com.example.sae_mobile_api.sae_mobile_api.models.Signalement;
import com.example.sae_mobile_api.sae_mobile_api.models.ResponseDataStructure.AggregateSignalementResponse;
import com.example.sae_mobile_api.sae_mobile_api.models.compositePK.SignalementPK;

public interface SignalementRepository extends JpaRepository<Signalement,SignalementPK>{
    @Query("SELECT s FROM Signalement s WHERE s.signalementPK_ID.idPharmacie.id = :idPharmacie")
    List<Signalement> findAllByIdPharmacie(@Param("idPharmacie") Long idPharmacie);

    @Query("SELECT s FROM Signalement s WHERE s.signalementPK_ID.CIP13.CIP13 IN :CIP13 AND s.signalementPK_ID.dateSignalement BETWEEN :dateDebut AND :dateFin")
    List<Signalement> findAllByMedicamentAndDateBetween(@Param("CIP13") List<Long>CIP13, @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query("SELECT s FROM Signalement s WHERE s.signalementPK_ID.idPersonne.id = :idPersonne ORDER BY s.signalementPK_ID.dateSignalement DESC")
    Page<Signalement> findAllByIdPersonneWithCustomOrder(@Param("idPersonne") int idPersonne, Pageable pageable);

    @Query("SELECT s FROM Signalement s WHERE s.signalementPK_ID.idPharmacie.id = :idPharmacie AND s.signalementPK_ID.dateSignalement>= :startDate ")
    List<Signalement> findSignalementFromLastWeek(@Param("idPharmacie") int idPharmacie,@Param("startDate") Date startDate); 
}
