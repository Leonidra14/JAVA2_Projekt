package project.dao.jpa;

import project.dao.AddressRepository;
import project.domain.Address;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface AddressJPARepository extends JpaRepository<Address, Long>, AddressRepository {
}
