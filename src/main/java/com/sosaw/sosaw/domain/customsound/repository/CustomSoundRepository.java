package com.sosaw.sosaw.domain.customsound.repository;

import com.sosaw.sosaw.domain.customsound.entity.CustomSound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSoundRepository extends JpaRepository<CustomSound, Long> {

}
