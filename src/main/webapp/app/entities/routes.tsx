import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pessoa from './pessoa';
import Responsavel from './responsavel';
import Telefone from './telefone';
import Endereco from './endereco';
import Turma from './turma';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="pessoa/*" element={<Pessoa />} />
        <Route path="responsavel/*" element={<Responsavel />} />
        <Route path="telefone/*" element={<Telefone />} />
        <Route path="endereco/*" element={<Endereco />} />
        <Route path="turma/*" element={<Turma />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
