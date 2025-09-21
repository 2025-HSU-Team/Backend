package com.sosaw.sosaw.domain.basicsound.repository;

import com.sosaw.sosaw.domain.basicsound.entity.BasicSound;
import com.sosaw.sosaw.domain.basicsound.entity.enums.BasicSoundType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicSoundRepository extends JpaRepository<BasicSound, Long> {

    Optional<BasicSound> findBySoundType(BasicSoundType sound);
}
