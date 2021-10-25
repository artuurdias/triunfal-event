using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using static System.Console;
using API.Models;
using API.Data;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ConvidadoController : Controller
    {
        private readonly Context _context;
        public ConvidadoController(Context context)
        {
            // construtor
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Convidado>>  GetAll()
        {
            return _context.Convidado.ToList();
        }

        [HttpGet("{idEvento}")]
        public ActionResult<List<Convidado>> GetById(string idEvento)
        {
            try
            {
                var convidados = _context.Convidado.ToArray();
                Convidado[] lista = new Convidado[convidados.Length];
                WriteLine($"Length: {convidados.Length}");

                int cont=0;

                for (int i=0; i < convidados.Length; i++) {
                    if (convidados[i].idEvento == idEvento) {
                        lista[i] = convidados[i];
                        cont++;
                    }
                }
                
                WriteLine($"Cont: {cont}");             

                Convidado[] todos = new Convidado[cont];

                for (int i=0; i < cont; i++) {
                    todos[i] = lista[i];
                }

                return todos.ToList();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. ");
            }
        }

        [HttpGet("{idEvento}/{username}")]
        public ActionResult<Convidado> GetByIdUsername(string idEvento, string username)
        {
            try
            {
                var convidados = _context.Convidado.ToArray();
                Convidado[] lista = new Convidado[convidados.Length];
                WriteLine($"Length: {convidados.Length}");

                
                for (int i=0; i < convidados.Length; i++) {
                    if (convidados[i].idEvento == idEvento && convidados[i].nomeUsuario == username) {     
                        WriteLine($"Convidado: {convidados[i].nomeUsuario} --- Evento: {convidados[i].idEvento}");
                        return convidados[i];      
                    }
                }    
                
                return _context.Convidado.Find(-1);
                    
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados. ");
            }
        }


        [HttpPost]
        public async Task<ActionResult> PostConvidado(Convidado convidado)
        {
            try
            {
                _context.Convidado.Add(convidado);

                if (await _context.SaveChangesAsync() == 1)
                    return Created($"/api/convidado/{convidado.idConvidado}", convidado);
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
                var convidado = await _context.Convidado.FindAsync(id);

                if (convidado == null)
                    return NotFound();

                _context.Remove(convidado);
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