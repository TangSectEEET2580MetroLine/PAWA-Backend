package rmit.edu.vn.hcmc_metro.metro_line;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetroLineRepository extends MongoRepository<MetroLine, String> {
    
    MetroLine findByName(String name);
}