import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseService from './cise-service';
import CiseServiceDetail from './cise-service-detail';
import CiseServiceUpdate from './cise-service-update';
import CiseServiceDeleteDialog from './cise-service-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CiseServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CiseServiceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CiseServiceDetail} />
      <ErrorBoundaryRoute path={match.url} component={CiseService} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CiseServiceDeleteDialog} />
  </>
);

export default Routes;
