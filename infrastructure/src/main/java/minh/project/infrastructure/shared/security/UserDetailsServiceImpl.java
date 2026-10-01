package minh.project.infrastructure.shared.security;
import lombok.RequiredArgsConstructor;
import minh.project.infrastructure.auth.jpa.UserJPARepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserJPARepository userJPARepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // don't know if there's any ways to throw this exception in another place....
        return userJPARepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email)
                );
    }

}
