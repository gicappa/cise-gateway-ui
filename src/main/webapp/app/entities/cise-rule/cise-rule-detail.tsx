import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cise-rule.reducer';
import { ICiseRule } from 'app/shared/model/cise-rule.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICiseRuleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CiseRuleDetail extends React.Component<ICiseRuleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { ciseRuleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="gatewayuiApp.ciseRule.detail.title">CiseRule</Translate> [<b>{ciseRuleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="gatewayuiApp.ciseRule.name">Name</Translate>
              </span>
            </dt>
            <dd>{ciseRuleEntity.name}</dd>
            <dt>
              <span id="ruleType">
                <Translate contentKey="gatewayuiApp.ciseRule.ruleType">Rule Type</Translate>
              </span>
            </dt>
            <dd>{ciseRuleEntity.ruleType}</dd>
            <dt>
              <span id="entityTemplate">
                <Translate contentKey="gatewayuiApp.ciseRule.entityTemplate">Entity Template</Translate>
              </span>
            </dt>
            <dd>{ciseRuleEntity.entityTemplate}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseRule.ciseServiceProfile">Cise Service Profile</Translate>
            </dt>
            <dd>{ciseRuleEntity.ciseServiceProfileId ? ciseRuleEntity.ciseServiceProfileId : ''}</dd>
            <dt>
              <Translate contentKey="gatewayuiApp.ciseRule.ciseRuleSet">Cise Rule Set</Translate>
            </dt>
            <dd>{ciseRuleEntity.ciseRuleSetId ? ciseRuleEntity.ciseRuleSetId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cise-rule" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/cise-rule/${ciseRuleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ ciseRule }: IRootState) => ({
  ciseRuleEntity: ciseRule.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CiseRuleDetail);
