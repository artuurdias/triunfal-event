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

        [HttpGet("{id}")]
        public ActionResult<Convite> GetById(int id)
        {
            try
            {
                return _context.Convite.Find(id);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> PostEvent(Convite evento)
        {
            try
            {
                _context.Convite.Add(evento);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/convite/{evento.id}", evento);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(int id)
        {
            try
            {
                var evento = await _context.Convite.FindAsync(id);

                if (evento == null)
                    return NotFound();

                _context.Remove(evento);
                await _context.SaveChangesAsync();

                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Put(int id, Convite evento)
        {
            try
            {
                var result = await _context.Convite.FindAsync(id);
                if (id != result.id)
                {
                    return BadRequest();
                }
                result.id = evento.id;
                result.data = evento.data;
                result.mensagem = evento.mensagem;
                result.idEvento = evento.idEvento;
                result.nomeUsuario = evento.nomeUsuario;

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