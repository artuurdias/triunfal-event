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
    public class OrganizadorController : Controller
    {
        private readonly Context _context;
        public OrganizadorController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Organizador>>  GetAll()
        {
            return _context.Organizador.ToList();
        }

        [HttpGet("{id}")]
        public ActionResult<Organizador> GetById(int id)
        {
            try
            {
                return _context.Organizador.Find(id);
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        public async Task<ActionResult> PostUser(Organizador organizador)
        {
            try
            {
                _context.Organizador.Add(organizador);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/organizador/{organizador.idOrganizador}", organizador);
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
                var organizador = await _context.Organizador.FindAsync(id);

                if (organizador == null)
                    return NotFound();

                _context.Remove(organizador);
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