import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Endereco from './endereco';
import EnderecoDetail from './endereco-detail';
import EnderecoUpdate from './endereco-update';
import EnderecoDeleteDialog from './endereco-delete-dialog';

const EnderecoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Endereco />} />
    <Route path="new" element={<EnderecoUpdate />} />
    <Route path=":id">
      <Route index element={<EnderecoDetail />} />
      <Route path="edit" element={<EnderecoUpdate />} />
      <Route path="delete" element={<EnderecoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnderecoRoutes;
