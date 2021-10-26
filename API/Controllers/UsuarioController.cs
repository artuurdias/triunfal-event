using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using API.Models;
using API.Data;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsuarioController : Controller
    {
        private readonly Context _context;
        public UsuarioController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Usuario>> GetAll()
        {
            return _context.Usuario.ToList();
        }

        [HttpGet("{username}")]
        public ActionResult<Usuario> GetByUsername(string username)
        {
            try
            {
                return _context.Usuario.Find(username);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. GetId");
            }
        }

        [HttpGet("{username}/eventos")]
        public ActionResult<List<Evento>> getUserEvents(string username)
        {
            var convidados = _context.Convidado.ToList();
            var eventos = _context.Evento.ToList();

            int cont = 0;
            var lista = new List<Evento>(convidados.Count);

            for (int i = 0; i < convidados.Count; i++)
                if (convidados[i].nomeUsuario == username)
                {
                    lista.Add(_context.Evento.Find(convidados[i].idEvento));
                    cont++;
                }
            
            for (int i = 0; i < eventos.Count; i++)
                if (eventos[i].organizador == username)
                {
                    lista.Add(eventos[i]);
                    cont++;
                }

            var newLista = new List<Evento>(cont);
            for (int i = 0; i < cont; i++)
                newLista.Add(lista[i]);
            
            return newLista;
        }

        [HttpPost]
        public async Task<ActionResult> PostUser(Usuario user)
        {
            try
            {
                _context.Usuario.Add(user);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/usuario/{user.username}", user);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. PostId");
            }
            return BadRequest();
        }

        [HttpDelete("{username}")]
        public async Task<ActionResult> Delete(string username)
        {
            try
            {
                var usuario = await _context.Usuario.FindAsync(username);

                if (usuario == null)
                    return NotFound();

                _context.Remove(usuario);
                await _context.SaveChangesAsync();
                
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. DeleteId");
            }
        }

        [HttpPut("{username}")]
        public async Task<IActionResult> Put(string username, Usuario usuario)
        {
            try
            {
                var result = await _context.Usuario.FindAsync(username);
                if (username != result.username)
                {
                    return BadRequest();
                }

                result.username = usuario.username;
                result.nome = usuario.nome;
                result.nascimento = usuario.nascimento;
                result.email = usuario.email;
                result.senha = usuario.senha;

                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. UpdateId" + $"{username}");
            }
        }
    }
}
