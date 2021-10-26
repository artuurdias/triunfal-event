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
    public class DicaController : Controller
    {
        private readonly Context _context;
        public DicaController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Dica>>  GetAll()
        {
            return _context.Dica.ToList();
        }

        [HttpGet("{tipoEvento}/{conteudo}")]
        public ActionResult<Dica> GetById(string tipoEvento, string conteudo)
        {
            try
            {
                var dica = _context.Dica.Find(tipoEvento, conteudo);
                
                if (dica == null) 
                    return NotFound();

                return dica;
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> Post(Dica dica)
        {
            try
            {
                _context.Dica.Add(dica);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/dica/{dica.tipoEvento}/{dica.conteudo}", dica);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            
            return BadRequest();
        }
    }
}
