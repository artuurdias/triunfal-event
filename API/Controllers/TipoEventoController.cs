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
    public class TipoEventoController : Controller
    {
        private readonly Context _context;
        public TipoEventoController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<TipoEvento>> GetAll()
        {
            return _context.TipoEvento.ToList();
        }

        [HttpGet("{nome}")]
        public ActionResult<TipoEvento> GetById(string nome)
        {
            try
            {
                var events = _context.TipoEvento.Find(nome);
                return events;
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> PostTipoEvento(TipoEvento evento)
        {
            try
            {
                _context.TipoEvento.Add(evento);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/tipoevento/{evento.id}", evento);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{nome}")]
        public async Task<ActionResult> Delete(string nome)
        {
            try
            {
                var evento = await _context.TipoEvento.FindAsync(nome);

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

        [HttpPut("{nome}")]
        public async Task<IActionResult> Put(string nome, TipoEvento evento)
        {
            try
            {
                var result = await _context.TipoEvento.FindAsync(nome);
                if (nome != result.nome)
                {
                    return BadRequest();
                }
                result.id           = evento.id;
                result.nome         = evento.nome;
                result.definicao    = evento.definicao;
                result.exemplos     = evento.exemplos;

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
