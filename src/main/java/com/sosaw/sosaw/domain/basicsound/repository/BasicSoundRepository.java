package com.sosaw.sosaw.domain.basicsound.repository;

import com.sosaw.sosaw.domain.basicsound.entity.BasicSound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicSoundRepository extends JpaRepository<BasicSound, Long> {

}
