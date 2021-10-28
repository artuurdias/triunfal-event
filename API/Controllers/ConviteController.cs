using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using API.Data;
using API.Models;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ConviteController : Controller
    {
        private readonly Context _context;
        public ConviteController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Convite>> GetAll()
        {
            return _context.Convite.ToList();
        }

        [HttpGet("{nomeUsuario}/{idEvento}/")]
        public ActionResult<Convite> GetByIdConvite(string nomeUsuario, string idEvento)
        {
            try
            {
                var convite = _context.Convite.Find(nomeUsuario, idEvento.ToUpper());

                if (convite == null)
                    return NotFound();

                return convite;
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. ");
            }
        }

        [HttpGet("user/{nomeUsuario}")]
        public ActionResult<List<Convite>> GetByIdUsername(string nomeUsuario)
        {
            try
            {
                //var convite = _context.Convite.ToList().Find();
                var convites = _context.Convite.ToList().FindAll(i => i.nomeUsuario == nomeUsuario);

                if (convites == null)
                    return NotFound();

                return convites;
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. ");
            }
        }

        [HttpGet("evento/{idEvento}")]
        public ActionResult<List<Convite>> GetByIdEvento(string idEvento)
        {
            try
            {
                //var convite = _context.Convite.ToList().Find();
                var convites = _context.Convite.ToList().FindAll(i => i.idEvento == idEvento);

                if (convites == null)
                    return NotFound();

                return convites;
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. ");
            }
        }

        [HttpPost]
        public async Task<ActionResult> PostEvent(Convite convite)
        {
            try
            {
                _context.Convite.Add(convite);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/convite/{convite.nomeUsuario}/{convite.idEvento}", convite);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{nomeUsuario}/{idEvento}")]
        public async Task<ActionResult> Delete(string nomeUsuario, string idEvento)
        {
            try
            {
                var convite = await _context.Convite.FindAsync(nomeUsuario, idEvento);

                if (convite == null)
                    return NotFound();

                _context.Remove(convite);
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
