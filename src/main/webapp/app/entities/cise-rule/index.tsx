import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseRule from './cise-rule';
import CiseRuleDetail from './cise-rule-detail';
import CiseRuleUpdate from './cise-rule-update';
import CiseRuleDeleteDialog from './cise-rule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CiseRuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CiseRuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CiseRuleDetail} />
      <ErrorBoundaryRoute path={match.url} component={CiseRule} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CiseRuleDeleteDialog} />
  </>
);

export default Routes;
