import pessoa from 'app/entities/pessoa/pessoa.reducer';
import responsavel from 'app/entities/responsavel/responsavel.reducer';
import telefone from 'app/entities/telefone/telefone.reducer';
import endereco from 'app/entities/endereco/endereco.reducer';
import turma from 'app/entities/turma/turma.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  pessoa,
  responsavel,
  telefone,
  endereco,
  turma,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
