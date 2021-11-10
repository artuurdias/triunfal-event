using System;
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
    public class EventoController : Controller
    {
        private readonly Context _context;
        public EventoController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Evento>>  GetAll()
        {
            return _context.Evento.ToList();
        }

        [HttpGet("{id}")]
        public ActionResult<Evento> GetById(string id)
        {
            try
            {
		        var events = _context.Evento.Find(id);
                return events;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

                
        [HttpGet("user/{username}")]
        public ActionResult<List<Evento>> GetByIdUsername(string username)
        {
            try
            {

                var convites    = _context.Convidado.ToList().FindAll(i => i.nomeUsuario == username);

                List<Evento> ret    = new List<Evento>(convites.Count);

                Evento evento = null;

                for(int i = 0; i < convites.Count; i++)
                {
                    evento = _context.Evento.Find(convites[i].idEvento);
                    ret.Add(evento);
                }

                return ret;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> PostEvent(Evento evento)
        {
            try
            {
                _context.Evento.Add(evento);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/evento/{evento.id}", evento);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Delete(string id)
        {
            try
            {
                var evento = await _context.Evento.FindAsync(id);

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
        public async Task<IActionResult> Put(string id, Evento evento)
        {
            try
            {
                var result = await _context.Evento.FindAsync(id);
                if (id != result.id)
                {
                    return BadRequest();
                }
                result.id = evento.id;
                result.nome = evento.nome;
                result.tipo = evento.tipo;
                result.data = evento.data;
                result.endereco = evento.endereco;
                result.organizador = evento.organizador;
                
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
