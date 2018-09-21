import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseRuleSet from './cise-rule-set';
import CiseRuleSetDetail from './cise-rule-set-detail';
import CiseRuleSetUpdate from './cise-rule-set-update';
import CiseRuleSetDeleteDialog from './cise-rule-set-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CiseRuleSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CiseRuleSetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CiseRuleSetDetail} />
      <ErrorBoundaryRoute path={match.url} component={CiseRuleSet} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CiseRuleSetDeleteDialog} />
  </>
);

export default Routes;
