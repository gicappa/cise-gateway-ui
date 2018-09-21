export const enum CiseRuleType {
  ALLOW_ALL_FILEDS = 'ALLOW_ALL_FILEDS',
  ALLOW_SPECIFIC_FILEDS = 'ALLOW_SPECIFIC_FILEDS',
  DENY_ALL_FIELDS = 'DENY_ALL_FIELDS'
}

export interface ICiseRule {
  id?: number;
  name?: string;
  ruleType?: CiseRuleType;
  entityTemplate?: string;
  ciseServiceProfileId?: number;
  ciseRuleSetId?: number;
}

export const defaultValue: Readonly<ICiseRule> = {};
