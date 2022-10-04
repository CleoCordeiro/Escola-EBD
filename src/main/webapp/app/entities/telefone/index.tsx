import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telefone from './telefone';
import TelefoneDetail from './telefone-detail';
import TelefoneUpdate from './telefone-update';
import TelefoneDeleteDialog from './telefone-delete-dialog';

const TelefoneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telefone />} />
    <Route path="new" element={<TelefoneUpdate />} />
    <Route path=":id">
      <Route index element={<TelefoneDetail />} />
      <Route path="edit" element={<TelefoneUpdate />} />
      <Route path="delete" element={<TelefoneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelefoneRoutes;
