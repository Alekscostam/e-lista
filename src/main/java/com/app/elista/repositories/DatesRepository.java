package com.app.elista.repositories;

import com.app.elista.model.Dates;
import com.app.elista.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DatesRepository extends JpaRepository<Dates, Long> {


    @Query("SELECT d FROM Dates d WHERE d.datesGroup = :date")
    Optional<Dates> findByDate(String date);

}
