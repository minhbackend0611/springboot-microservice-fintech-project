package minh.project.infrastructure.shared.adapter;

import com.fasterxml.uuid.Generators;
import minh.project.application.shared.port.IdGeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidV7GeneratorService implements IdGeneratorService {

    @Override
    public UUID nextId() {
        return Generators.timeBasedEpochGenerator().generate();
    }
}
