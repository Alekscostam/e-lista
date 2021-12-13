package com.app.elista.repositories;

import com.app.elista.model.Dates;
import com.app.elista.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DatesRepository extends JpaRepository<Dates, Long> {


}
