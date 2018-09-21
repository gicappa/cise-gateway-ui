import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import ciseAuthority, {
  CiseAuthorityState
} from 'app/entities/cise-authority/cise-authority.reducer';
// prettier-ignore
import ciseService, {
  CiseServiceState
} from 'app/entities/cise-service/cise-service.reducer';
// prettier-ignore
import ciseRuleSet, {
  CiseRuleSetState
} from 'app/entities/cise-rule-set/cise-rule-set.reducer';
// prettier-ignore
import ciseRule, {
  CiseRuleState
} from 'app/entities/cise-rule/cise-rule.reducer';
// prettier-ignore
import ciseServiceProfile, {
  CiseServiceProfileState
} from 'app/entities/cise-service-profile/cise-service-profile.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly ciseAuthority: CiseAuthorityState;
  readonly ciseService: CiseServiceState;
  readonly ciseRuleSet: CiseRuleSetState;
  readonly ciseRule: CiseRuleState;
  readonly ciseServiceProfile: CiseServiceProfileState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  ciseAuthority,
  ciseService,
  ciseRuleSet,
  ciseRule,
  ciseServiceProfile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
