import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Turma from './turma';
import TurmaDetail from './turma-detail';
import TurmaUpdate from './turma-update';
import TurmaDeleteDialog from './turma-delete-dialog';

const TurmaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Turma />} />
    <Route path="new" element={<TurmaUpdate />} />
    <Route path=":id">
      <Route index element={<TurmaDetail />} />
      <Route path="edit" element={<TurmaUpdate />} />
      <Route path="delete" element={<TurmaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TurmaRoutes;
