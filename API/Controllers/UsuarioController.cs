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
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
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
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
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
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
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
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }
    }
}