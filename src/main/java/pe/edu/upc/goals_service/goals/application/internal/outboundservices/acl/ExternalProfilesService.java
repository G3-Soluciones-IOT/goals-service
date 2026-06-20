package pe.edu.upc.goals_service.goals.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;

@Service("goalsExternalProfilesService")
public class ExternalProfilesService {

    public boolean existsProfile(Long profileId) {
        return profileId != null && profileId > 0;
    }
}
