import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Responsavel from './responsavel';
import ResponsavelDetail from './responsavel-detail';
import ResponsavelUpdate from './responsavel-update';
import ResponsavelDeleteDialog from './responsavel-delete-dialog';

const ResponsavelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Responsavel />} />
    <Route path="new" element={<ResponsavelUpdate />} />
    <Route path=":id">
      <Route index element={<ResponsavelDetail />} />
      <Route path="edit" element={<ResponsavelUpdate />} />
      <Route path="delete" element={<ResponsavelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ResponsavelRoutes;
