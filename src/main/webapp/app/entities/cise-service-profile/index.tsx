import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseServiceProfile from './cise-service-profile';
import CiseServiceProfileDetail from './cise-service-profile-detail';
import CiseServiceProfileUpdate from './cise-service-profile-update';
import CiseServiceProfileDeleteDialog from './cise-service-profile-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CiseServiceProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CiseServiceProfileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CiseServiceProfileDetail} />
      <ErrorBoundaryRoute path={match.url} component={CiseServiceProfile} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CiseServiceProfileDeleteDialog} />
  </>
);

export default Routes;
