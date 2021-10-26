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
    public class FeriadoController : Controller
    {
        private readonly Context _context;
        public FeriadoController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Feriado>>  GetAll()
        {
            return _context.Feriado.ToList();
        }

        [HttpGet("{nome}")]
        public ActionResult<Feriado> GetById(string nome)
        {
            try
            {
                var feriado = _context.Feriado.Find(nome);

                if (feriado == null)
                    return NotFound();

                return feriado;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> Post(Feriado feriado)
        {
            try
            {
                _context.Feriado.Add(feriado);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/feriado/{feriado.nome}", feriado);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }
    }
}
