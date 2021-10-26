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
    public class LembreteController : Controller
    {
        private readonly Context _context;
        public LembreteController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Lembrete>>  GetAll()
        {
            return _context.Lembrete.ToList();
        }

        [HttpGet("{usuario}/{data}")]
        public ActionResult<Lembrete> GetById(string usuario, string data)
        {
            try
            {
                var lembrete = _context.Lembrete.Find(usuario, data);

                if (lembrete == null)
                    return NotFound();

                return lembrete;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> Post(Lembrete lembrete)
        {
            try
            {
                _context.Lembrete.Add(lembrete);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/lembrete/{lembrete.usuario}/{lembrete.data}", lembrete);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }

        [HttpDelete("{usuario}/{data}")]
        public async Task<ActionResult> Delete(string usuario, string data)
        {
            try
            {
                var lembrete = await _context.Lembrete.FindAsync(usuario, data);

                if (lembrete == null)
                    return NotFound();

                _context.Remove(lembrete);
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
