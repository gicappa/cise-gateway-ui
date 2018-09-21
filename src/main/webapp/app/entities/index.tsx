import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CiseAuthority from './cise-authority';
import CiseService from './cise-service';
import CiseRuleSet from './cise-rule-set';
import CiseRule from './cise-rule';
import CiseServiceProfile from './cise-service-profile';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/cise-authority`} component={CiseAuthority} />
      <ErrorBoundaryRoute path={`${match.url}/cise-service`} component={CiseService} />
      <ErrorBoundaryRoute path={`${match.url}/cise-rule-set`} component={CiseRuleSet} />
      <ErrorBoundaryRoute path={`${match.url}/cise-rule`} component={CiseRule} />
      <ErrorBoundaryRoute path={`${match.url}/cise-service-profile`} component={CiseServiceProfile} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
