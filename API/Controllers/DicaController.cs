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

        [HttpGet("{id}")]
        public ActionResult<Dica> GetById(int id)
        {
            try
            {
                return _context.Dica.Find(id);
            }
            catch 
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }
    }
}