package sga.datos;

import java.util.List;
import sga.domain.Usuario;

public interface UsuarioDao {
    public List<Usuario> findAllUsuarios();
    
    public Usuario findUsuarioById(Usuario usuario);
    
    public void insertUsuario(Usuario usuario);
    
    public void updateUsuario(Usuario usuario);
    
    public void deleteUsuario(Usuario usuario);
    
}
