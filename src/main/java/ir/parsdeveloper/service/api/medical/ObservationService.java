package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Observation;
import ir.parsdeveloper.model.entity.core.User;

/**
 * Created by bahram on 6/1/16.
 */
public interface ObservationService {
    void addObservation(Observation observation, User user)throws ServiceException;
}
