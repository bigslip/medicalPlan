package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Observation;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.ObservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bahram on 6/1/16.
 */
@Service
public class DefaultObservationService extends DefaultPersonService<Observation> implements ObservationService {
    @Override
    @Transactional
    public void addObservation(Observation observation, User user) throws ServiceException {
        saveEntity(observation, user);
    }
}
