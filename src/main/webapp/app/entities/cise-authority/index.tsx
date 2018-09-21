import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseAuthority from './cise-authority';
import CiseAuthorityDetail from './cise-authority-detail';
import CiseAuthorityUpdate from './cise-authority-update';
import CiseAuthorityDeleteDialog from './cise-authority-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CiseAuthorityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CiseAuthorityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CiseAuthorityDetail} />
      <ErrorBoundaryRoute path={match.url} component={CiseAuthority} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CiseAuthorityDeleteDialog} />
  </>
);

export default Routes;
