/*
 * MazeGame DSL - Content Assist
 * 
 * Custom content assist proposals for the DSL editor.
 */
package main.game.maze.dsl.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

/**
 * Provides custom content assist proposals for MazeDsl.
 * 
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 */
public class MazeDslProposalProvider extends AbstractMazeDslProposalProvider {

    @Override
    public void completeGameConfiguration_Name(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("MyLevel", "Level name", null, context));
        acceptor.accept(createCompletionProposal("Tutorial", "Tutorial level", null, context));
        acceptor.accept(createCompletionProposal("Challenge", "Challenge level", null, context));
    }

    @Override
    public void completeOpponentConfig_Name(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("Enemy1", "Basic enemy", null, context));
        acceptor.accept(createCompletionProposal("Boss", "Boss enemy", null, context));
        acceptor.accept(createCompletionProposal("Minion", "Minion enemy", null, context));
    }

    @Override
    public void completePatrolConfig_Name(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("MainPath", "Main patrol path", null, context));
        acceptor.accept(createCompletionProposal("GuardRoute", "Guard route", null, context));
        acceptor.accept(createCompletionProposal("PerimeterPatrol", "Perimeter patrol", null, context));
    }

    @Override
    public void completeOpponentConfig_Health(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("50", "Low health", null, context));
        acceptor.accept(createCompletionProposal("100", "Normal health", null, context));
        acceptor.accept(createCompletionProposal("200", "High health", null, context));
        acceptor.accept(createCompletionProposal("500", "Boss health", null, context));
    }

    @Override
    public void completeOpponentConfig_ThreatLevel(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("5", "Very low threat", null, context));
        acceptor.accept(createCompletionProposal("10", "Low threat", null, context));
        acceptor.accept(createCompletionProposal("25", "Medium threat", null, context));
        acceptor.accept(createCompletionProposal("50", "High threat", null, context));
        acceptor.accept(createCompletionProposal("100", "Maximum threat", null, context));
    }

    @Override
    public void completeDifficultyConfig_MaxThreat(EObject model, Assignment assignment,
            ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
        acceptor.accept(createCompletionProposal("20", "Easy - max 20 threat", null, context));
        acceptor.accept(createCompletionProposal("50", "Normal - max 50 threat", null, context));
        acceptor.accept(createCompletionProposal("100", "Hard - max 100 threat", null, context));
    }
}
